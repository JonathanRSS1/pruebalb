package jonsthan.ramirez.Max.proyectoformativo.ui.home

import Modelos.PACIENTES
import Modelos.Conexion
import RecyclerViewHelpers.ADAPTADOR
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jonsthan.ramirez.Max.proyectoformativo.LOGIN
import jonsthan.ramirez.Max.proyectoformativo.R
import jonsthan.ramirez.Max.proyectoformativo.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val UserNamesession = root.findViewById<TextView>(R.id.lblUserName)
        UserNamesession.text = LOGIN.nameSession

        fun getPatients(): List<PACIENTES> {
            val objCon = Conexion().StringConection()
            val statement = objCon?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM TBPatients")!!

            val listPatients = mutableListOf<PACIENTES>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("UUID_Patients")
                val name = resulSet.getString("Name")
                val lastName = resulSet.getString("LastName")
                val Age = resulSet.getInt("Age")
                val Disease = resulSet.getString("Disease")
                val RoomNumber = resulSet.getInt("RoomNumber")
                val BedNumber = resulSet.getInt("BedNumber")
                val Medication = resulSet.getString("Medication")
                val AdmmissioDate = resulSet.getString("AddmissionDate")
                val MedicationtTime = resulSet.getString("MedicationTime")

                val values = PACIENTES(uuid, name, lastName, Age, Disease, RoomNumber,BedNumber,Medication,AdmmissioDate,MedicationtTime)
                listPatients.add(values)
            }

            return listPatients
        }

        val rcvPatients = root.findViewById<RecyclerView>(R.id.rcvPatients)
        rcvPatients.layoutManager = LinearLayoutManager(this.context)

        GlobalScope.launch(Dispatchers.IO)
        {
            val patients = getPatients()
            withContext(Dispatchers.Main)
            {
                val adapter = ADAPTADOR(patients)
                rcvPatients.adapter = adapter
            }
        }
        return root
    }
}