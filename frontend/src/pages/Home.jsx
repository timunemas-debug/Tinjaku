import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

function Home() {
  return (
    <>
      <Navbar />

      <section className="bg-gradient-to-r from-blue-600 to-cyan-500 text-white">

        <div className="max-w-7xl mx-auto px-6 py-28 text-center">

          <h1 className="text-5xl font-bold leading-tight">
            Solusi Sedot WC
            <br />
            Cepat & Profesional
          </h1>

          <p className="mt-6 text-xl">
            Pesan layanan sedot WC kapan saja,
            mudah, cepat, dan terpercaya.
          </p>

          <button className="mt-10 bg-white text-blue-600 px-8 py-4 rounded-xl font-semibold hover:scale-105 duration-300">
            Pesan Sekarang
          </button>

        </div>

      </section>

      <section className="py-24 bg-gray-50">

        <div className="max-w-7xl mx-auto">

          <h2 className="text-4xl font-bold text-center">
            Mengapa Memilih Kami?
          </h2>

          <div className="grid md:grid-cols-4 gap-8 mt-16">

            <div className="bg-white p-8 rounded-xl shadow">
              <h3 className="text-xl font-semibold">
                Respon Cepat
              </h3>
            </div>

            <div className="bg-white p-8 rounded-xl shadow">
              <h3 className="text-xl font-semibold">
                Harga Transparan
              </h3>
            </div>

            <div className="bg-white p-8 rounded-xl shadow">
              <h3 className="text-xl font-semibold">
                Teknisi Profesional
              </h3>
            </div>

            <div className="bg-white p-8 rounded-xl shadow">
              <h3 className="text-xl font-semibold">
                Layanan 24 Jam
              </h3>
            </div>

          </div>

        </div>

      </section>

      <Footer />
    </>
  );
}

export default Home;