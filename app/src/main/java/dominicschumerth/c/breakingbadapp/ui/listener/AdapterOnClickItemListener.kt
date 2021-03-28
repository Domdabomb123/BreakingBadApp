package dominicschumerth.c.breakingbadapp.ui.listener

interface AdapterOnClickItemListener<M> {
    fun onItemClicked(position: Int, item: M)
}