package jonsthan.ramirez.Max.proyectoformativo


import Modelos.Conexion
import android.content.Intent
import android.os.Bundle
import kotlinx.coroutines.withContext
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LOGIN : AppCompatActivity() {


    companion object
    {
        lateinit var nameSession: String
        lateinit var uuidSession: String
        lateinit var usernameSession: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linkRegister = findViewById<TextView>(R.id.lkRecoverPassword)
        val txtUser = findViewById<TextView>(R.id.txtUser)
        val txtPassword = findViewById<TextView>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        fun clear()
        {
            txtUser.setText("")
            txtPassword.setText("")
        }

        linkRegister.setOnClickListener{
            clear()
            val register = Intent(this@LOGIN, REGISTRO::class.java)
            startActivity(register)
        }

        btnLogin.setOnClickListener{
            GlobalScope.launch (Dispatchers.IO ) {
                try
                {
                    if(txtUser.text.isEmpty() || txtPassword.text.isEmpty())
                    {
                        GlobalScope.launch (Dispatchers.IO) {

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@LOGIN, "Complete los campos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else
                    {
                        val objCon = Conexion().StringConection()

                        val userData = objCon?.prepareStatement("SELECT Name,UUID_Users FROM TBUsers WHERE NameUser = ? AND Password = ?")!!
                        userData.setString(1, txtUser.text.toString())
                        userData.setString(2, txtPassword.text.toString())
                        val result = userData.executeQuery()

                        if(result.next())
                        {
                            val intent = Intent(this@LOGIN, MainActivity::class.java)
                            //TODO: Varibles globales para información del usuario
                            uuidSession = result.getString("UUID_Users")
                            nameSession = result.getString("Name")
                            usernameSession = txtUser.text.toString()
                            startActivity(intent)

                        }
                        else
                        {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@LOGIN, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }


                }
                catch(e: Exception)
                {
                    println("Error: $e")
                }

            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val txtU = findViewById<EditText>(R.id.txtUser)
        val txtP = findViewById<EditText>(R.id.txtPassword)

        txtP.setText("")
        txtU.setText("")
    }

}