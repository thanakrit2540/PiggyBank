package buu.informatics.s59160102.piggybankv2


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import buu.informatics.s59160102.piggybankv2.addBank.AddBankViewModel
import buu.informatics.s59160102.piggybankv2.addBank.AddBankViewModelFactory
import buu.informatics.s59160102.piggybankv2.database.BankDatabase
import buu.informatics.s59160102.piggybankv2.databinding.FragmentAddBinding
import buu.informatics.s59160102.piggybankv2.databinding.FragmentUpdateBinding
import buu.informatics.s59160102.piggybankv2.updateBank.UpdateViewModel
import buu.informatics.s59160102.piggybankv2.updateBank.UpdateViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_update.*

/**
 * A simple [Fragment] subclass.
 */
const val KEY_SUMEDIT = "sumEdit_key"
private var sum : Int = 0
class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_update,container,false)

//        Log.i("UpdateFragment", "Called ViewModelProviders.of")
//        viewModel = ViewModelProviders.of(this).get(AddBankViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val arguments = UpdateFragmentArgs.fromBundle(arguments!!)
        val dataSource = BankDatabase.getInstance(application).bankDatabaseDao
        val viewModelFactory = UpdateViewModelFactory(dataSource,arguments.sumId ,application)
        val UpdateViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(UpdateViewModel::class.java)
        binding.updateViewModel = UpdateViewModel
        binding.setLifecycleOwner(this)
        binding.UpdateBtn.setOnClickListener {
            if(EditSum_input.text.toString().length > 0 ){
                UpdateViewModel.onUpdate(EditSum_input.text.toString())
                Toast.makeText(context, "Update Now!", Toast.LENGTH_SHORT).show()
                UpdateViewModel.onClose()
            }else{
                Toast.makeText(context, "Check input values not null!!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.DeleteBtn.setOnClickListener {
            Toast.makeText(context, "Delete Now!", Toast.LENGTH_SHORT).show()
            UpdateViewModel.onDelete()
            UpdateViewModel.onClose()
        }

        UpdateViewModel.onSetValue(sum)
        Log.i("Update",sum.toString())
        if (savedInstanceState != null) {
            sum = savedInstanceState.getInt(KEY_SUMEDIT, 0)
        }

        UpdateViewModel.navigateToListBank.observe(this, Observer {
            if (it==true){
                hideKeyboard()
                this.findNavController().navigate(
                    UpdateFragmentDirections.actionUpdateFragmentToListFragment()
                )
                UpdateViewModel.doneNavigating()
            }
        })

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)

    }

    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "จำนวนเงินที่ทำการแก้ไข : "+binding.EditSumInput.text+" บาท")
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
    @SuppressLint("ServiceCast")
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }


}
