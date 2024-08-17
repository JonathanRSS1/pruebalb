package jonsthan.ramirez.Max.proyectoformativo.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jonsthan.ramirez.Max.proyectoformativo.LOGIN
import jonsthan.ramirez.Max.proyectoformativo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_perfil, container, false)
        val btnLogOut = root.findViewById<Button>(R.id.btnLogOut)
        val userName = root.findViewById<TextView>(R.id.lblUserProfile)
        val name = root.findViewById<TextView>(R.id.lblNameProfile)
        val uuid = root.findViewById<TextView>(R.id.lblUUIDprofile)

        GlobalScope.launch(Dispatchers.IO)
        {
            btnLogOut.setOnClickListener {  startActivity(Intent(requireContext(), LOGIN::class.java))  }
        }

        name.text = LOGIN.nameSession
        userName.text = LOGIN.usernameSession
        uuid.text = LOGIN.uuidSession

        return root
    }
}