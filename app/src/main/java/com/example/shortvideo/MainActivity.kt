package com.example.shortvideo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.LoadAdError

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var videoList: List<String>
    private var mInterstitialAd: InterstitialAd? = null
    private var adCounter = 0 // untuk kontrol frekuensi iklan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi AdMob
        MobileAds.initialize(this) {}

        loadInterstitialAd()

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        videoList = assets.list("video")?.toList() ?: emptyList()
        recyclerView.adapter = VideoAdapter(videoList, this)

        // scroll ke tengah untuk infinite loop aman
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)

        val btnUp: Button = findViewById(R.id.btnUp)
        val btnDown: Button = findViewById(R.id.btnDown)

        btnUp.setOnClickListener {
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            recyclerView.smoothScrollToPosition(firstVisible - 1)
            tryShowAd()
        }

        btnDown.setOnClickListener {
            val lastVisible = layoutManager.findLastVisibleItemPosition()
            recyclerView.smoothScrollToPosition(lastVisible + 1)
            tryShowAd()
        }
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-1732022362331933/1684243294", // ganti dengan Interstitial Unit ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }
            })
    }

    private fun tryShowAd() {
        adCounter++
        if (adCounter % 5 == 0) { // misal tiap 5 scroll / klik tombol
            mInterstitialAd?.show(this)
            loadInterstitialAd() // load ad berikutnya
        }
    }
}
