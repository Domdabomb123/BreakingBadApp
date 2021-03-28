package dominicschumerth.c.breakingbadapp.injection

class Injector {
    private lateinit var appComponent: AppComponent

    companion object {
        private lateinit var sInstance: Injector

        fun init(interactorModule: InteractorModule) {
            sInstance = Injector()
            sInstance.appComponent = DaggerAppComponent.builder().interactorModule(interactorModule).build()
        }

        val appInstance: AppComponent
            get() = sInstance.appComponent
    }
}