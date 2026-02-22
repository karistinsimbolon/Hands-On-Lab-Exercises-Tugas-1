## Latihan 1
## Eksperimen 1
Buka /test/view  → Apa yang muncul?   Halaman HTML (template test.html) dengan tulisan: "Ini dari @Controller"<br>
Buka /test/text  → Apa yang muncul?  Ini dari @Controller + @ResponseBody → text langsung<br>
Apa perbedaannya? -/test/view merender template HTML.<br>
                  -/test/text menampilkan text langsung tanpa template. <br>
Kesimpulan:<br>
@Controller tanpa @ResponseBody → return nama template
Dengan @ResponseBody → return data langsung (text/JSON)<br>

## Eksperimen 2
Apakah berhasil?[Tidak]<br>
HTTP Status code: 500<br>
Error message: Error resolving template<br>
Kesimpulan:<br>
Jika Controller return nama view yang tidak ada, Spring akan mengembalikan error 500 (Internal Server Error) karena 
template tidak ditemukan oleh Thymeleaf.<br>

## Eksperimen 3
| URL                         | Hasil di Halaman         |
|-----------------------------|--------------------------|
| /greet                      | Selamat Pagi, Mahasiswa! |
| /greet?name=Budi            | Selamat Pagi, Budi!      |
| /greet?name=Budi&waktu=Siang| Selamat Siang, Budi!     |
| /greet/Ani                  | Halo, Ani!               |
| /greet/Ani/detail           | Halo, Ani!               |
| /greet/Ani/detail?lang=EN   | Hello, Ani!              | 

Jawaban Pertanyaan<br>
URL mana yang pakai @RequestParam? /greet, /greet?name=, /greet?name= &waktu= <br>
URL mana yang pakai @PathVariable? /greet/Ani, /greet/Ani/detail <br>
URL mana yang pakai keduanya? /greet/Ani/detail?lang=EN <br>

## Jawaban Refleksi
1. **Apa perbedaan antara `@Controller` dan `@RestController`? Dalam kasus apa kamu pakai masing-masing?**
    - @Controller untuk menampilkan halaman HTML.
    - @RestController untuk mengembalikan data JSON/text (API).
2. **Perhatikan bahwa template `product/list.html` dipakai oleh 3 endpoint berbeda (list all, filter by category, search). Apa keuntungannya membuat template yang reusable seperti ini?**
    - Kode lebih rapi, tidak duplikasi, dan mudah di-maintain.
3. **Kenapa Controller inject `ProductService` (bukan langsung akses data di ArrayList)? Apa yang terjadi kalau Controller langsung manage data?**
    - Agar mengikuti konsep MVC. Controller hanya menangani request, logic ada di Service.
4. **Apa perbedaan `model.addAttribute("products", products)` dengan return `products` langsung seperti di `@RestController`?**
    - model.addAttribute → kirim data ke template HTML.
    - Return langsung (@RestController) → kirim data JSON ke client.
5. **Jika kamu buka `http://localhost:8080/products/abc` (ID bukan angka), apa yang terjadi? Kenapa?**
    - Akan error karena abc bukan angka dan tidak bisa dikonversi ke Long.
6. **Apa keuntungan pakai `@RequestMapping("/products")` di level class dibanding menulis full path di setiap `@GetMapping`?**
    - Menghindari penulisan path berulang dan membuat kode lebih rapi.
7. **Dalam lab ini, kata "Model" muncul dalam beberapa konteks berbeda. Sebutkan minimal 2 arti yang berbeda dan jelaskan perbedaannya.**
   Hint: perhatikan `Model` di parameter method Controller, folder `model/`, dan class `Product`.
      - Model di Controller → untuk kirim data ke View.
      - Folder model/ dan class Product → representasi data aplikasi.


## Latihan 2

### Eksperimen 1: Fragment Tidak Ada
- Apakah error?:             [Ya]
- Error message:             [type=Internal Server Error, status=500].
  KESIMPULAN : Jika nama fragment salah, Thymeleaf akan menampilkan error (TemplateInputException) dan halaman tidak bisa dirender.
### Eksperimen 2: Static Resource
- CSS masih bekerja?:         [Ya]
- Apakah halaman error?:      [Tidak]
- Apakah CSS diterapkan?:     [Tidak]
  KESIMPULAN : Kesimpulan: th:href="@{}" lebih baik karena Thymeleaf akan membantu mengelola path secara otomatis sesuai dengan context path aplikasi.
  Jadi lebih aman dan fleksibel. Jika file CSS tidak ada, halaman tetap bisa dibuka tetapi CSS tidak diterapkan karena file tidak ditemukan (404).

### Pertanyaan Refleksi
1. Kalau kita pakai fragment, navbar dan footer cukup dibuat satu kali saja, misalnya di file fragments/navbar.
   html dan fragments/footer.html.Lalu di halaman lain tinggal dipanggil.
   Kenapa ini enak?
   - Tidak perlu copy–paste navbar di setiap halaman
   - Kalau mau ubah menu, cukup ubah di satu tempat
   - Kode jadi lebih rapi dan tidak berulang-ulang
   - Lebih mudah dirawat (maintenance)
2.  - Folder templates/ itu untuk file HTML yang diproses oleh Thymeleaf. Jadi biasanya dipakai untuk halaman yang ada datanya dari Controller.
- Sedangkan folder static/ itu untuk file biasa seperti CSS, JavaScript, dan gambar. File di dalam static/ bisa langsung dibaca oleh browser.
- CSS ada di static/ karena CSS itu cuma file tampilan biasa, tidak perlu diproses oleh Thymeleaf
3.  - th:replace itu artinya mengganti tag yang ada dengan isi fragment. Jadi tag lama hilang, diganti isi yang baru.
- th:insert itu artinya memasukkan isi fragment ke dalam tag yang sudah ada. Jadi tag lamanya tetap ada.
  Sederhananya:
  th:replace → diganti total
  th:insert → dimasukkan ke dalam
4. Karena @{} itu otomatis menyesuaikan dengan alamat aplikasi (context path).
   Kalau nanti alamat aplikasi berubah, URL tetap aman dan tidak error.
   Jadi lebih fleksibel dan lebih aman dibanding tulis langsung /products misalnya.
5. Kalau langsung pakai new, memang bisa jalan, tapi itu bukan cara yang benar di Spring.
   Kalau pakai Dependency Injection (DI), Spring yang akan mengatur dan membuat object-nya. Itu lebih rapi, lebih mudah diatur, dan lebih mudah untuk testing.
   Jadi lebih baik pakai DI daripada buat object sendiri di dalam Controller.