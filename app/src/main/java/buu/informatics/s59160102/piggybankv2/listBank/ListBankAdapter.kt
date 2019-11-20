package buu.informatics.s59160102.piggybankv2.listBank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import buu.informatics.s59160102.piggybankv2.database.Bank
import buu.informatics.s59160102.piggybankv2.databinding.ListItemBankBinding


class BankDiffCallback : DiffUtil.ItemCallback<Bank>() {
    override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem.bankId == newItem.bankId
    }

    override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
        return oldItem == newItem
    }
}
class ListBankAdapter(val clickListener: ListBankListener) :  ListAdapter<Bank, ListBankAdapter.ViewHolder>(BankDiffCallback()) {

    class ViewHolder(val binding: ListItemBankBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Bank,
            clickListener: ListBankListener
        ) {
            binding.clickListener = clickListener
            binding.bank = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBankBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.textSum.text = "Sum : "+item.sum.toString()
        holder.bind(item!!, clickListener)

    }


}
class ListBankListener(val clickListener: (bankId: Long) -> Unit) {
    fun onClick(bank: Bank) = clickListener(bank.bankId)
}