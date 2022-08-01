package com.numq.mvarchitecture.image.mvp.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import arrow.core.Option
import arrow.core.none
import arrow.core.some

abstract class BasePresenter<T : BaseView> : LifecycleEventObserver {

    private var view: Option<T> = none()

    fun attachView(v: T) {
        if (view.isEmpty()) view = v.some()
    }

    private fun detachView() {
        if (view.isNotEmpty()) view = none()
    }

    fun display(scope: T.() -> Unit) {
        view.tap {
            it.apply(scope)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event.targetState) {
            Lifecycle.State.DESTROYED -> {
                detachView()
            }
            else -> Unit
        }
    }

}