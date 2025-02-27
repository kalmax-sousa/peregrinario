package com.example.peregrinario.repository

import android.content.Context
import android.util.Log
import com.example.peregrinario.R
import com.example.peregrinario.dao.UserDao
import com.example.peregrinario.data.PeregrinarioDatabase
import com.example.peregrinario.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class AuthRepository (context: Context) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userDao: UserDao = PeregrinarioDatabase.getDatabase(context).userDao()

    //Cadastro de usuários
    suspend fun registerUser(email:String, password:String, name:String):Boolean{
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid

            if(uid != null){
                val user = User(id = uid, name = name, email = email, isLoggedIn = true, profilePicUrl = null)
                Log.d("TESTE", user.toString())
                firestore.collection("users").document(uid).set(user).await()

                userDao.insertUser(user)
            }
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error registering user: ${e.message}")
            false
        }
    }

    //Login com email e senha
    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            user?.let{
                val uid = it.uid
                val name = firestore.collection("users").document(uid).get().await().getString("name") ?: "Usuário"

                val localUser = User(id = uid, name = name, email = email, isLoggedIn = true, profilePicUrl = "")

                // Salvar localmente no Room
                userDao.insertUser(localUser)
            }

            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error logging in user: ${e.message}")
            false
        }
    }

    // Login com Google
    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    suspend fun loginWithGoogle(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user

            user?.let {
                val uid = it.uid
                val name = it.displayName ?: "Usuário"
                val email = it.email ?: ""

                // Verifica se o usuário já existe no Firestore antes de salvar
                val userRef = firestore.collection("users").document(uid)
                val snapshot = userRef.get().await()

                if (!snapshot.exists()) {
                    val localUser = User(id = uid, name = name, email = email, isLoggedIn = true, profilePicUrl = "")
                    userRef.set(localUser).await()

                    userDao.insertUser(localUser)
                }
            }
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro no login Google: ${e.message}")
            false
        }
    }

    suspend fun resetPassword(email: String): Boolean {
        return try {
            auth.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error resetting password: ${e.message}")
            false
        }
    }

    suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUser()
        }
    }

    // Logout
    suspend fun logout() {
        auth.signOut()
        withContext(Dispatchers.IO) {
            userDao.deleteUser() // Remove o usuário do Room ao deslogar
        }
    }

    // Verifica se o usuário está logado
    suspend fun isUserLogged(): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.getUser() != null
        }
    }

    suspend fun anonymousLogin(){
        val anonymousUser = User(id = UUID.randomUUID().toString(), name = "Visitante", email = null, isLoggedIn = false, profilePicUrl = "")
        userDao.insertUser(anonymousUser)
    }

}