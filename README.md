# Tugas Besar 1 - Android IF3210 Pengembangan Aplikasi pada Platform Khusus

Sebuah aplikasi pada Platform Android bernama Perlu Dilindungi.

Tujuan tugas besar ini:

1. Memahami prinsip dasar dalam platform Android
2. Bekerja dalam tim pada konteks pengembangan aplikasi Android
3. Mempresentasikan perangkat lunak yang sudah dibangun

## Spesifikasi

1. Menampilkan Berita COVID-19
2. Menampilkan Daftar Faskes untuk Vaksinasi
3. Menampilkan Detail Informasi Faskes
4. Menampilkan Daftar Bookmark Faskes
5. Melakukan "Check-In"

## Cara Kerja

Pada aplikasi ini, terdapat beberapa layar yang dibuat, diantaranya layar Berita COVID-19, Daftar Faskes untuk Vaksinasi, Detail Informasi Faskes, Daftar Bookmark Faskes, dan layar "Check-In".

Keseluruhan layar dibuat menggunakan layout dan fragment kecuali layar "Check-In" yang dibuat menggunakan activity. Pada setiap layar, terdapat View Model yang digunakan untuk menyimpan data yang tertera pada layar agar ketika terjadi perubahan konfigurasi aplikasi atau pengguna berpindah layar maka data yang ditampilkan pada aplikasi tetap sama. Selain itu di dalam View Model terdapat data yang disimpan di dalam Live Data agar apabila terjadi perubahan isi data, maka tampilan layar di Layout akan langsung berubah.

Untuk mengambil data dari API yang disediakan, kami menggunakan library Retrofit untuk melakukan HTTP Request dan Moshi untuk melakukan parsing data. Sedangkan untuk mengambil data dari basis data yang terdapat di perangkat Android, kami menggunakan Room dan Dao.

Untuk setiap layar yang bisa melakukan scrolling, seperti layar Berita COVID-19, Daftar Faskes untuk Vaksinasi, dan Daftar Bookmark Faskes, kami menggunakan Recycler View dengan Adapternya masing-masing.

Untuk bagian News hanya tinggal mengklik berita yang ingin dibaca.

Untuk bagian mencari Faskes hanya tinggal memilih daerah faskes yang ingin dicari dan akan diberikan pilihan faskes apa saja yang ada. Untuk melihat informasi faskes hanya perlu mengklik faskes tersebut pada halaman mencari faskes.

Pada informasi faskes dapat dilakukan interaksi untuk mencari letak faskes pada Google Maps dan menambahkannya ke Bookmark untuk faskes yang ingin kita simpan informasinya. Untuk pengaksesan faskes yang sudah dibookmark hanya perlu menuju menu Bookmark yang ada pada bottombar.

Untuk bagian check-in untuk mencapai halaman check-in tersebut hanya perlu menekan floating action button yang tersedia pada halaman News, Search Faskes, dan Bookmark Faskes. Kemudian akan dimintai permission untuk menggunakan kamera karena membutuhkan kamera untuk melakukan scan suatu QR Code yang sudah tersedia. Kemudian anda hanya tinggal memposisikan QR Code pada kamera yang ada dan akan muncul hasil dari hasil scan QR Code tersebut pada bagian bawah apakah berhasil check-in atau tidak. Kemudian ada termometer yang akan mengukur suhu lingkungan (suhu gawai) pada bagian kanan atas.

## Perpustakaan

- Material : Digunakan untuk menggunakan component layout yang sudah tersedia dan mengikuti standar Google.
- Lifecycle-Libraries : Digunakan untuk menggunakan View Model dan juga Live Data.
- Navigation-Libraries : Digunakan untuk melakukan navigasi antar fragment.
- Room-Libraries : Digunakan untuk mengakses fitur Room untuk menyimpan data secara mudah.
- Play-Service-Location : Digunakan untuk mendapatkan lokasi pengguna.
- Retrofit : Digunakan untuk melakukan HTTP Request dan Response.
- Moshi : Digunakan untuk melakukan parsing data dari HTTP Response.
- Coil : Digunakan untuk mengambil dan menampilkan gambar dari URL yang diberikan.
- Scanner : Digunakan untuk membaca QR Code yang tersedia.
- Syntethic-Binding : Ekstensi untuk mengakses tiap-tiap komponen layout tanpa memanggil findviewbyid

## Tampilan Aplikasi

1. Tampilan News dan Isi dari News (Darkmode dan Lightmode)
<img src="/uploads/b82c80e6bb88f3859e41cf70d7000c0a/News_DM.png"  width="150" height="300">
<img src="/uploads/6be2132ff0f201d65ac71127ae116eac/News_LM.png" width="150" height="300">
<img src="/uploads/e6f8163d35b61e1a84e77f5eec85bb4e/News-detail_DM.png" width="150" height="300">
<img src="/uploads/8633f504e4c4db50a0fd8f7f15d14992/News-detal_LM.png" width="150" height="300">

2. Tampilan List Faskes (Darkmode dan Lightmode)
<img src="/uploads/beec9ff450e65234762b465906b2b5e1/List_Faskes_DM.png" width="150" height="300">
<img src="/uploads/3b56a62ef1cc8abe9b35c256f398fb0b/List_Faskes-Search_DM.png" width="150" height="300">
<img src="/uploads/b368f158b59f997a4c3288d86ed5a7ce/Screen_Shot_2022-03-05_at_12.23.52.png" width="150" height="300">
<img src="/uploads/1d72cb42ed3cccbf49defc7c9105ad5c/List_Faskes-Search_LM.png" width="150" height="300">
<img src="/uploads/4bb5cf6dd88638626a92e88188fb5c82/List_Faskes-Horizontal_DM.jpg" width="300" height="150">
<img src="/uploads/bf1d5368783b4f0b4b4bc41de7412513/List_Faskes-Horizontal_LM.jpg" width="300" height="150">

3. Tampilan Detail Faskes (Darkmode dan Lightmode)
<img src="/uploads/ba25933ff784d67136bad1a87cfe0771/Screen_Shot_2022-03-05_at_15.42.11.png" width="150" height="300">
<img src="/uploads/f01f259f8f30a0407d804bc888901ab9/994096.jpg" width="150" height="300">
<img src="/uploads/52c0f8e15202b9e7703cd0e85e6a38de/994097.jpg" width="150" height="300">
<img src="/uploads/bfc93c92cc3d74d0df59fb0c40058c50/Detail_Faskes_DM.jpg" width="150" height="300">
<img src="/uploads/a0b687fd0294939391f1c7c9574073f3/Detail_Faskes-un_DM.jpg" width="150" height="300">

4. Tampilan Bookmark (Darkmode dan Lightmode)
<img src="/uploads/6945abab2f3cbe7d25e751b3fe4ae3cb/Screen_Shot_2022-03-05_at_15.42.58.png" width="150" height="300">
<img src="/uploads/85949503e3ff5378bf268add35820ca1/Screen_Shot_2022-03-05_at_15.42.20.png" width="150" height="300">

5. Tampilan Scanner (Darkmode dan Lightmode)
<img src="/uploads/46403375eb31ed974d7cf3ba3aea980c/Screen_Shot_2022-03-05_at_15.49.41.png" width="150" height="300">
<img src="/uploads/54fc2a4f37e12d239ac6023c57b639b3/Screen_Shot_2022-03-05_at_15.49.03.png" width="150" height="300">
<img src="/uploads/0a02c904401f6bfe442bd4885b2b4773/Screen_Shot_2022-03-05_at_15.48.45.png" width="150" height="300">

## Pembuat

- Muhammad Azhar Faturahman (13519020)
- Dwianditya Hanif Raharjanto (13519046)
- Syamil Cholid Abdurrasyid (13519052)

## Pembagian Kerja

- Berita COVID-19 : 13519020
- Daftar Faskes untuk Vaksinasi : 13519020
- Detail Informasi Faskes : 13519052
- Daftar Bookmark Faskes : 13519052
- Melakukan "Check-In" : 13519046
