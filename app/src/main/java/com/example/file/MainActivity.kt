package com.example.file

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.file.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //表示中のディレクトリ
    //表示ディレクトリを変更するには、getRootDirectoryを変更する
    private var currentDir: File = Environment.getRootDirectory()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.filelist
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        if(hasPermission()) showFiles()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(!grantResults.isEmpty()&&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            showFiles()
        }else{
            finish()
        }
    }

    override fun onBackPressed() {
        if(currentDir != Environment.getRootDirectory()){
            currentDir = currentDir.parentFile
            showFiles()
        }else{
            super.onBackPressed()
        }
    }

    private fun showFiles(){
        val adapter = FilesAdapter(this,
            currentDir.listFiles().toList()){file->
            if(file.isDirectory){
                currentDir = file
                showFiles()
            }else{
                Toast.makeText(this,
                 file.absolutePath, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter
        //アプリバーに表示中のディレクトリパスを設定する
        title = currentDir.path
    }

    private fun hasPermission() : Boolean{
        //パーミッションを持っているか確認
        if(ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
                //持っていないのでパーミッションを要求
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            return false
        }
        return true
    }
}