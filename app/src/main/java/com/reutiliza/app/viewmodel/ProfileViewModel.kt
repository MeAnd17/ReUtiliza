package com.reutiliza.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.reutiliza.app.data.model.Badge
import com.reutiliza.app.data.model.BadgeCatalog
import com.reutiliza.app.data.model.User
import com.reutiliza.app.data.repository.AuthRepository
import com.reutiliza.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _user   = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _badges = MutableStateFlow<List<Badge>>(emptyList())
    val badges: StateFlow<List<Badge>> = _badges

    private val _leaderboard = MutableStateFlow<List<User>>(emptyList())
    val leaderboard: StateFlow<List<User>> = _leaderboard

    init { loadProfile() }

    fun loadProfile() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepo.getUserFlow(uid).collect { user ->
                _user.value = user
                // Marcar qué insignias están desbloqueadas
                _badges.value = BadgeCatalog.all.map { badge ->
                    badge.copy(isUnlocked = badge.id in user.badges)
                }
            }
        }
        viewModelScope.launch {
            userRepo.getLeaderboard().fold(
                onSuccess = { _leaderboard.value = it },
                onFailure = { }
            )
        }
    }

    fun logout() = authRepo.logout()

    fun updateProfile(name: String, bio: String) {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepo.updateProfile(uid, name, bio)
        }
    }
}
