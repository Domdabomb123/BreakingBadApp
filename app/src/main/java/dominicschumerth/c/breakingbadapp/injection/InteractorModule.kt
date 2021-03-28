package dominicschumerth.c.breakingbadapp.injection

import dagger.Module
import dagger.Provides
import dominicschumerth.c.breakingbadapp.network.interactor.BreakingBadInteractor

@Module
class InteractorModule {

    @Provides
    fun providesBreakingBadInteractor(): BreakingBadInteractor {
        return BreakingBadInteractor()
    }

}