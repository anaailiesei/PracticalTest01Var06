package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01Var06SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)

        val val1 = intent.getStringExtra("key1")
        val val2 = intent.getStringExtra("key2")
        val val3 = intent.getStringExtra("key3")

        val num = intent.getIntExtra("key4", 0)
        var gained = false

        if (val1 == "*") {
            if (val2 == "*") {
                gained = true
            } else {
                if (val3 == "*") {
                    gained = true
                } else {
                    gained = val2 == val3
                }
            }
        } else {
            if (val2 == "*") {
                if (val3 == "*") {
                    gained = true
                } else {
                    gained = val1 == val3
                }
            } else {
                if (val3 == "*") {
                    gained = val2 == val1
                } else {
                    gained = (val1 == val2) && (val1 == val3)
                }
            }
        }




        var scor : Int

        if (!gained) {
            scor = 0
        } else if (num == 0) {
            scor =100
        } else if (num ==1) {
            scor = 50
        } else {
            scor  = 10
        }

        var data : String
        if (gained) {
            data = "Gained $scor"
        } else {
            data = "0"
        }
        (findViewById(R.id.gained_text_view) as TextView).setText(data)

        // Presupunând că vrei să returnezi rezultatul la un anumit eveniment, de exemplu, la apăsarea unui buton
        val someButton: Button = findViewById(R.id.ok)
//
        someButton.setOnClickListener {
            val returnIntent = Intent().apply {
                putExtra("another_key", scor)
            }
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        // Dacă activitatea se încheie fără a seta explicit un rezultat, poți să nu faci nimic sau să setezi RESULT_CANCELED
    }
}