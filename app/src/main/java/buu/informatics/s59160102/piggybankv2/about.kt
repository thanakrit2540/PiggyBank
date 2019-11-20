package buu.informatics.s59160102.piggybankv2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import buu.informatics.s59160102.piggybankv2.databinding.FragmentAboutBinding
import kotlinx.android.synthetic.main.fragment_about.*


/**
 * A simple [Fragment] subclass.
 */
class about : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAboutBinding>(inflater,R.layout.fragment_about,container,false)

        binding.BackAboutBtn.setOnClickListener { view : View->
            view.findNavController().navigate(R.id.action_about3_to_startPageFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
