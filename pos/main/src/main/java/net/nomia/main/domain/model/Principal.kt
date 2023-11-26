package net.nomia.main.domain.model

import kotlinx.coroutines.flow.StateFlow

class Principal(val auth: StateFlow<Auth>)
