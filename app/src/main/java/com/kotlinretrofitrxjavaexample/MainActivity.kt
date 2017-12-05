package com.kotlinretrofitrxjavaexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.segunfamisa.kotlin.samples.retrofit.data.kotlin.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposal: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = SearchRepositoryProvider.provideSearchRepository()

        compositeDisposal.add(
                repository.searchUsers("Lagos", "Java")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe ({
                            result ->
                            Log.d("Result", "There are ${result.items.size} Java developers in Lagos")
                        }, { error ->
                            error.printStackTrace()
                        })
        )
    }

    override fun onDestroy() {
        compositeDisposal.clear()
        super.onDestroy()
    }
}
