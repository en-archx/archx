package co.en.archx.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.en.archx.sample.transferobjects.MainEvent
import co.en.archx.sample.transferobjects.MainState
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class MainActivity : AppCompatActivity(), MainView {

    private val eventRelay = PublishRelay.create<MainEvent>()

    private val stateRelay = BehaviorRelay.create<MainState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun events(): Observable<MainEvent> {
        return eventRelay
    }

    override fun render(state: MainState) {
        stateRelay.accept(state)
    }
}
