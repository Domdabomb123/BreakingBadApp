package dominicschumerth.c.breakingbadapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dominicschumerth.c.breakingbadapp.MainActivity
import dominicschumerth.c.breakingbadapp.R
import dominicschumerth.c.breakingbadapp.databinding.MainFragmentBinding
import dominicschumerth.c.breakingbadapp.model.Character
import dominicschumerth.c.breakingbadapp.ui.listener.AdapterOnClickItemListener
import dominicschumerth.c.breakingbadapp.ui.main.adapter.MainAdapter

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, AdapterOnClickItemListener<Character>, SearchView.OnQueryTextListener {

    private lateinit var viewBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModel.MainViewModelFactory
    private lateinit var adapter: MainAdapter
    private var mHandler: Handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        viewModelFactory = MainViewModel.MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setUpViews()
        startObserving()
        fetchData()

        setHasOptionsMenu(true)

        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        (menu.findItem(R.id.search)?.actionView as SearchView).queryHint = "Name or Season"
        (menu.findItem(R.id.search)?.actionView as SearchView).setOnQueryTextListener(this)
    }

    private fun setUpViews() {
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.addItemDecoration(dividerItemDecoration)
        viewBinding.recyclerView.setHasFixedSize(false)

        adapter = MainAdapter(ArrayList())
        viewBinding.recyclerView.adapter = adapter
        adapter.setListener(this)

        viewBinding.swipeLayout.setOnRefreshListener(this)
    }

    private fun startObserving() {
        viewModel.getCharacters.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setCharacters(it)
                viewBinding.swipeLayout.isRefreshing = false
            }
        })
    }

    private fun fetchData() {
        viewModel.fetchCharacters()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onRefresh() {
        fetchData()
    }

    override fun onItemClicked(position: Int, item: Character) {
        (activity as MainActivity).navController?.navigate(MainFragmentDirections.actionMainToDetail(item))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
            viewModel.filterCharacters(query.trim())
        } else {
            fetchData()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null && newText.isNotEmpty()) {
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed({
                viewModel.filterCharacters(newText.trim())
            }, 300)
        } else {
            fetchData()
        }
        return true
    }

}
