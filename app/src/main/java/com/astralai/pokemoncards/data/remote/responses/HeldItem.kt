package com.astralai.pokemoncards.data.remote.responses

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)