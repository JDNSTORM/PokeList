package com.navorjames.pokelist.core.data.network.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

internal actual val PlatformEngine: HttpClientEngine = Android.create()