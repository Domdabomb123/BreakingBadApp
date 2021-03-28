package dominicschumerth.c.breakingbadapp.ui.main

import androidx.lifecycle.*
import dominicschumerth.c.breakingbadapp.injection.Injector
import dominicschumerth.c.breakingbadapp.network.interactor.BreakingBadInteractor
import dominicschumerth.c.breakingbadapp.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    init {
        Injector.appInstance.inject(this)
    }

    @Inject lateinit var breakingBadInteractor: BreakingBadInteractor

    private val characters: MutableLiveData<List<Character>?> = MutableLiveData()

    val getCharacters: LiveData<List<Character>?>
        get() {
            return characters
        }

    fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            characters.postValue(breakingBadInteractor.fetchCharacters())
        }
    }

    fun filterCharacters(query:String) {
        var list = ArrayList<Character>()

        val queries = query.split(",")
        characters.value?.let { cList ->
            for (character in cList) {
                var addCharacter = true
                for (q in queries) {
                    if (!character.name.toLowerCase().contains(q.trim().toLowerCase()) && !character.nickname.toLowerCase().contains(q.trim().toLowerCase())) {
                        if (q.trim().toIntOrNull() != null) {
                            if (!character.seasons.contains(q.trim())) {
                                addCharacter = false
                            }
                        } else {
                            addCharacter = false
                        }
                    }
                }
                if (addCharacter) {
                    list.add(character)
                }
            }
        }
        characters.postValue(list)
    }

    class MainViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}
