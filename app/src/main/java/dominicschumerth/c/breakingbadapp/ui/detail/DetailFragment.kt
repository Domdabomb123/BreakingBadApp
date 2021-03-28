package dominicschumerth.c.breakingbadapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dominicschumerth.c.breakingbadapp.R
import dominicschumerth.c.breakingbadapp.databinding.DetailFragmentBinding
import dominicschumerth.c.breakingbadapp.model.Character

class DetailFragment : Fragment() {

    private lateinit var viewBinding: DetailFragmentBinding
    private var character:Character? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (arguments?.containsKey("character") == true) {
            character = arguments?.getSerializable("character") as Character
        }

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)

        renderUI()

        return viewBinding.root
    }

    private fun renderUI() {
        character?.let { c ->
            context?.let { Glide.with(it).load(c.image).into(viewBinding.ivDetailImage) }
            viewBinding.tvDetailName.text = c.name
            viewBinding.tvDetailOccupation.text = c.occupations.concat()
            viewBinding.tvDetailStatus.text = c.status
            viewBinding.tvDetailNickname.text = c.nickname
            viewBinding.tvDetailSeason.text = c.seasons.concat()
        }

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun List<String>.concat() = if (this.size > 1) {
        this.joinToString(", ") { it }
    } else {
        if (this.isNotEmpty()) {
            this[0]
        } else {
            ""
        }
    }
}