package ru.musindev.myapp

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.musindev.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button2 = findViewById<View>(R.id.favorites).setOnClickListener { Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show() }
        val button3 = findViewById<View>(R.id.watch_later).setOnClickListener { Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show() }
        val button4 = findViewById<View>(R.id.selections).setOnClickListener { Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show() }
        val button5 = findViewById<View>(R.id.settings).setOnClickListener { Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show() }

        binding.mainRecycler?.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    //Создаем бандл и кладем туда объект с данными фильма
                    val bundle = Bundle()
                    //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
                    //передаваемый объект
                    bundle.putParcelable("film", film)
                    //Запускаем наше активити
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    //Прикрепляем бандл к интенту
                    intent.putExtras(bundle)
                    //Запускаем активити через интент
                    startActivity(intent)
                }

                override fun click(film: Film, position: Int) {
                    TODO("Not yet implemented")
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(this@MainActivity)
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
//Кладем нашу БД в RV
        filmsAdapter.addItems(dataBase)

    }

    private val dataBase = listOf(
        Film("Хватай и беги", R.drawable.film_01, "Суперзвезда хоккея Бобби полностью посвятил свою жизнь спорту. Очаровательная Дэниз думает только о своем маленьком сыне, которого она пытается оградить от дурной компании."),
        Film("Убежище", R.drawable.film_02, "Сержант Рик Педрони, возвращается домой к своей жене Кейт изменившимся и опасным после того, как подвергся нападению таинственной силы во время боевых действий в Афгани"),
        Film("Вышибалы", R.drawable.film_03, "Для сына босса мафии ошибки и чувства - непростительная роскошь. Мэтти пришлось дорого заплатить, чтобы понять это. После долгих поисков «законной» работы он обращается к отцу, и тот дает ему шанс… Друг детства Мэтти, кокаинист Марблс, теряет сумку с деньгами."),
        Film("Собачий побег", R.drawable.film_04, "Золотистый ретривер Томи и красотка-спаниель Амалу проводят все время вместе, но их владельцы не одобряют роман из-за предрассудков. Не в силах противостоять великой собачьей любви, четвероногая пара сбегает и отправляется в путешествие, полное приключений."),
        Film("Сталкер. Черная плесень", R.drawable.film_05, "Когда фотограф Брук и ее друг пробираются в заброшенный дом в поисках адреналина и интересных кадров, их похищает параноик. Казалось бы, что может быть хуже? Но похититель — далеко не единственная угроза в этом особняке."),
        Film("Ставки сделаны", R.drawable.film_06, "Два мелких мошенника - очаровательная Бетти и остроумный, импозантный Виктор - сумели провести крупных торговцев наркотиками. Без труда выбрав и соблазнив подходящую жертву, хорошенькая Бетти подсыпает ему в вино снотворное, потом «сладкая» парочка изымает большую часть денег в общий «семейный» котел. Но однажды в сети попадает симпатичный наркопосредник с чемоданом - сейфом, содержащим 50 млн... франков."),
        Film("Эбигейл", R.drawable.film_07, "Банда преступников похищает 12-летнюю балерину, дочь криминального авторитета. Спрятавшись в уединённом особняке, они ещё не догадываются, что влиятельный отец девочки — далеко не главная их проблема."),
        Film("Сенсация", R.drawable.film_08, "Основанная на реальных событиях драма рассказывает о том, как сотрудницы программы «Ньюснайт» организовали скандально известное интервью с принцем Эндрю."),
        Film("Девочка Тори", R.drawable.film_09, "После исчезновения сына пара присоединяется к другим родителям, чья дочь пропала без вести в школе. Разочарованные работой полиции, они берут под свой контроль школу в поисках ответов."),
        Film("След киллера", R.drawable.film_10, "Полиция Чикаго так и не смогла поймать серийного убийцу, который несколько лет держал город в страхе. Но однажды он исчез — так же неожиданно, как и появился. Все забыли о жестоком преступнике. Все, кроме детектива, который расследовал его дело. Спустя пять лет в Шотландии начали происходить убийства с тем же почерком. Детективу придется отправиться на другой конец света, чтобы, наконец, поймать убийцу и отомстить за смерть своей любимой."),
    )

}

class TopSpacingItemDecoration (private val paddingInDp: Int): RecyclerView.ItemDecoration() {
    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingInDp.convertPx
        outRect.right = paddingInDp.convertPx
        outRect.left = paddingInDp.convertPx

    }
}
