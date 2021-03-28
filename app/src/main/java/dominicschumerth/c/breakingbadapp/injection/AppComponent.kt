package dominicschumerth.c.breakingbadapp.injection

import dagger.Component
import dominicschumerth.c.breakingbadapp.BreakingBadApplication
import dominicschumerth.c.breakingbadapp.network.interactor.BreakingBadInteractor
import dominicschumerth.c.breakingbadapp.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [InteractorModule::class])
interface AppComponent {

    //ViewModels
    fun inject(mainViewModel: MainViewModel)

    //Interactors
    fun inject(breakingBadInteractor: BreakingBadInteractor)

    //BreakingBadApplication
    fun inject(breakingBadApplication: BreakingBadApplication)

}