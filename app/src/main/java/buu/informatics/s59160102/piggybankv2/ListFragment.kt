package buu.informatics.s59160102.piggybankv2


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import buu.informatics.s59160102.piggybankv2.database.BankDatabase
import buu.informatics.s59160102.piggybankv2.listBank.ListBankViewModel
import buu.informatics.s59160102.piggybankv2.listBank.ListBankViewModelFactory

import buu.informatics.s59160102.piggybankv2.databinding.FragmentListBinding
import buu.informatics.s59160102.piggybankv2.listBank.ListBankAdapter
import buu.informatics.s59160102.piggybankv2.listBank.ListBankListener


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentListBinding>(inflater,
            R.layout.fragment_list,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = BankDatabase.getInstance(application).bankDatabaseDao
        val viewModelFactory = ListBankViewModelFactory(dataSource,application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListBankViewModel::class.java)

        binding.listViewModel = viewModel
//        binding.setLifecycleOwner(this)

        val adapter = ListBankAdapter(ListBankListener { bankId ->
//            Toast.makeText(context, "${bankId}", Toast.LENGTH_SHORT).show()
             viewModel.onBankClicked(bankId)
        })
//        viewModel.sum.observe(this, Observer { newSum ->
//            binding.textSum.text = newSum.toString()
//        })
        binding.bankList.adapter = adapter
        viewModel.banks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToEdit.observe(this, Observer { sum ->
            sum?.let {
                this.findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToUpdateFragment(sum)
                )
//                viewModel.onEditNavigated()
            }
        })
//        binding.textSum.setOnClickListener{
//            viewModel.onBankClicked(17)
//            viewModel.navigateToEdit.observe(this, Observer { sum ->
//                sum?.let {
//                   Log.i("ListBankFragment",sum.toString())
//                    this.findNavController().navigate(
//                        ListFragmentDirections.actionListFragmentToUpdateFragment(sum)
//                    )
//                    viewModel.onEditNavigated()
//                }
//            })
//        }
        viewModel.navigateToInsert.observe(this, Observer { click ->
            click?.let {
                this.findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToAddFragment()
                )
//                viewModel.doneNavigating()
            }
        })





        setHasOptionsMenu(true)
        Log.i("AddFragment",viewModel.sum.toString())
        val manager = GridLayoutManager(activity, 1)
        binding.bankList.layoutManager = manager as RecyclerView.LayoutManager
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }


}
