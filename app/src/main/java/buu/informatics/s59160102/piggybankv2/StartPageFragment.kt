package buu.informatics.s59160102.piggybankv2


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import buu.informatics.s59160102.piggybankv2.databinding.FragmentStartPageBinding

/**
 * A simple [Fragment] subclass.
 */
class StartPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStartPageBinding>(inflater,
            R.layout.fragment_start_page,container,false)


        binding.StartBtn.setOnClickListener { view : View->
            view.findNavController().navigate(StartPageFragmentDirections.actionStartPageFragmentToListFragment( ))
        }
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)   
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }




}
