package testing.steven.cloudinteractiveinterview.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import testing.steven.cloudinteractiveinterview.R


class MainActivity : AppCompatActivity() , View.OnClickListener {
    override fun onClick(v: View?) {
        if(v==btn_next){
            goToNextPage()
        }
    }

    private fun goToNextPage() {
        val intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_next.setOnClickListener(this)
    }



}
