package buu.informatics.s59160102.piggybankv2


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import buu.informatics.s59160102.piggybankv2.addBank.AddBankViewModel
import buu.informatics.s59160102.piggybankv2.addBank.AddBankViewModelFactory
import buu.informatics.s59160102.piggybankv2.database.BankDatabase
import buu.informatics.s59160102.piggybankv2.databinding.FragmentAddBinding


/**
 * A simple [Fragment] subclass.
 */
const val KEY_SUM = "sum_key"
private var sum : Int = 0
class AddFragment : Fragment() {







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddBinding>(inflater,
            R.layout.fragment_add,container,false)

        Log.i("AddFragment", "Called ViewModelProviders.of")
//        viewModel = ViewModelProviders.of(this).get(AddBankViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = BankDatabase.getInstance(application).bankDatabaseDao
        val viewModelFactory = AddBankViewModelFactory(dataSource, application)
        val AddBankViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(AddBankViewModel::class.java)
//        if (savedInstanceState != null) {
//            sum = savedInstanceState.getInt(KEY_SUM, 0)
//        }

        AddBankViewModel.onCreateFinish()
        binding.ConfirmBtn.setOnClickListener { view : View ->
            Log.i("AddFragment", "ได้มะ")
            AddBankViewModel.eventCreateFinish.observe(this, Observer<Boolean> { hasFinished ->
                Toast.makeText(activity, "Create Suscess", Toast.LENGTH_SHORT).show()

                if (hasFinished){
                    AddBankViewModel.onCreate(sum)
                    view.findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())
                    AddBankViewModel.onGameFinishComplete()
                }
            })

        }
        binding.CancelBtn.setOnClickListener { view : View ->
            view.findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())
        }

        binding.Coin10Image.setOnClickListener {
            AddBankViewModel.onClickTen()
            Log.i("AddFragment", AddBankViewModel.sum.value.toString())
            binding.MoneyText.text = AddBankViewModel.sum.value.toString()
            sum = AddBankViewModel.sum.value?:0
        }
        binding.Coin5Image.setOnClickListener {
            AddBankViewModel.onClickFive()
            Log.i("AddFragment", AddBankViewModel.sum.value.toString())
            binding.MoneyText.text = AddBankViewModel.sum.value.toString()
            sum = AddBankViewModel.sum.value?:0
        }
        binding.Coin2Image.setOnClickListener {
            AddBankViewModel.onClickTwo()
            Log.i("AddFragment", AddBankViewModel.sum.value.toString())
            binding.MoneyText.text = AddBankViewModel.sum.value.toString()
            sum = AddBankViewModel.sum.value?:0
        }
        binding.Coin1Image.setOnClickListener {
            AddBankViewModel.onClickOne()
            Log.i("AddFragment", AddBankViewModel.sum.value.toString())
            binding.MoneyText.text = AddBankViewModel.sum.value.toString()
            sum = AddBankViewModel.sum.value?:0
        }


        //
        binding.addBankViewModel = AddBankViewModel
//        binding.lifecycleOwner = this
//        binding.setLifecycleOwner(this)


        Log.i("AddFragment",  binding.MoneyText.toString())
        // Inflate the layout for this fragment
        return binding.root
    }


}
