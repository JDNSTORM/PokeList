package com.example.pokelist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.pokelist.databinding.LoadStatePokemonsBinding
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

class PokemonsLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PokemonsAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: PokemonsAdapter.ViewHolder, loadState: LoadState) {
        val binding = holder.binding as LoadStatePokemonsBinding
        binding.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokemonsAdapter.ViewHolder {
        return PokemonsAdapter.ViewHolder(
            LoadStatePokemonsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private fun LoadStatePokemonsBinding.bind(state: LoadState){
        if (state is LoadState.Error) {
            val message = getErrorMessage(state.error)
            errorMsg.text = message
        }
        progressBar.isVisible = state is LoadState.Loading
        btnRetry.isVisible = state is LoadState.Error
        errorMsg.isVisible = state is LoadState.Error

        btnRetry.setOnClickListener { retry.invoke() }
    }

    private fun getErrorMessage(error: Throwable): String{
        error.printStackTrace()
        return when(error){
            is SocketTimeoutException -> "Socket Timeout"
            is IOException -> "IOException"
            is HttpException -> { when(error.code()){
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> "Request Timeout"
                HttpURLConnection.HTTP_FORBIDDEN -> "Invalid Request. Please wait."
                else -> "Error encountered with the server"
            } }
            else -> "Error occurred"
        }
    }
}