package dominicschumerth.c.breakingbadapp

import android.app.Application
import dominicschumerth.c.breakingbadapp.injection.Injector
import dominicschumerth.c.breakingbadapp.injection.InteractorModule

class BreakingBadApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.init(InteractorModule())
        Injector.appInstance.inject(this)
    }

}