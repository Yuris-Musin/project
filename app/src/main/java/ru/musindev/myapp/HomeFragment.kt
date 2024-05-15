package ru.musindev.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ru.musindev.myapp.databinding.FragmentHomeBinding
import java.util.Locale


class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val filmsDataBase = listOf(
        Film(
            "Хватай и беги",
            R.drawable.film_01,
            "Суперзвезда хоккея Бобби полностью посвятил свою жизнь спорту. Очаровательная Дэниз думает только о своем маленьком сыне, которого она пытается оградить от дурной компании."
        ),
        Film(
            "Убежище",
            R.drawable.film_02,
            "Сержант Рик Педрони, возвращается домой к своей жене Кейт изменившимся и опасным после того, как подвергся нападению таинственной силы во время боевых действий в Афгани"
        ),
        Film(
            "Вышибалы",
            R.drawable.film_03,
            "Для сына босса мафии ошибки и чувства - непростительная роскошь. Мэтти пришлось дорого заплатить, чтобы понять это. После долгих поисков «законной» работы он обращается к отцу, и тот дает ему шанс… Друг детства Мэтти, кокаинист Марблс, теряет сумку с деньгами."
        ),
        Film(
            "Собачий побег",
            R.drawable.film_04,
            "Золотистый ретривер Томи и красотка-спаниель Амалу проводят все время вместе, но их владельцы не одобряют роман из-за предрассудков. Не в силах противостоять великой собачьей любви, четвероногая пара сбегает и отправляется в путешествие, полное приключений."
        ),
        Film(
            "Сталкер. Черная плесень",
            R.drawable.film_05,
            "Когда фотограф Брук и ее друг пробираются в заброшенный дом в поисках адреналина и интересных кадров, их похищает параноик. Казалось бы, что может быть хуже? Но похититель — далеко не единственная угроза в этом особняке."
        ),
        Film(
            "Ставки сделаны",
            R.drawable.film_06,
            "Два мелких мошенника - очаровательная Бетти и остроумный, импозантный Виктор - сумели провести крупных торговцев наркотиками. Без труда выбрав и соблазнив подходящую жертву, хорошенькая Бетти подсыпает ему в вино снотворное, потом «сладкая» парочка изымает большую часть денег в общий «семейный» котел. Но однажды в сети попадает симпатичный наркопосредник с чемоданом - сейфом, содержащим 50 млн... франков."
        ),
        Film(
            "Эбигейл",
            R.drawable.film_07,
            "Банда преступников похищает 12-летнюю балерину, дочь криминального авторитета. Спрятавшись в уединённом особняке, они ещё не догадываются, что влиятельный отец девочки — далеко не главная их проблема."
        ),
        Film(
            "Сенсация",
            R.drawable.film_08,
            "Основанная на реальных событиях драма рассказывает о том, как сотрудницы программы «Ньюснайт» организовали скандально известное интервью с принцем Эндрю."
        ),
        Film(
            "Девочка Тори",
            R.drawable.film_09,
            "После исчезновения сына пара присоединяется к другим родителям, чья дочь пропала без вести в школе. Разочарованные работой полиции, они берут под свой контроль школу в поисках ответов."
        ),
        Film(
            "След киллера",
            R.drawable.film_10,
            "Полиция Чикаго так и не смогла поймать серийного убийцу, который несколько лет держал город в страхе. Но однажды он исчез — так же неожиданно, как и появился. Все забыли о жестоком преступнике. Все, кроме детектива, который расследовал его дело. Спустя пять лет в Шотландии начали происходить убийства с тем же почерком. Детективу придется отправиться на другой конец света, чтобы, наконец, поймать убийцу и отомстить за смерть своей любимой."
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }

                    override fun click(film: Film, position: Int) { }
                })


            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
//Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String?): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText!!.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запроси и имя фильма приводить к нижнему регистру
                    it.title.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }

}