package engineer.kaobei.Viewmodel


import com.google.gson.Gson
import engineer.kaobei.BASE_URL
import engineer.kaobei.KaobeiEngineerService
import engineer.kaobei.Model.Articles.Article
import engineer.kaobei.Model.Articles.KaobeiArticleList
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * A ViewModel used for the {@link ArticleListFragment}.
 */
class ArticleListViewModel : ListViewModel<Article>() {

    fun loadMoreArticles(page: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(KaobeiEngineerService::class.java)
        service.articleList(page.toString()).enqueue(object:retrofit2.Callback<KaobeiArticleList>{
            override fun onFailure(call: retrofit2.Call<KaobeiArticleList>, t: Throwable) {
                mOnReceiveDataListener?.onFailureToReceiveData()
            }

            override fun onResponse(
                call: retrofit2.Call<KaobeiArticleList>,
                response: retrofit2.Response<KaobeiArticleList>
            ) {
                if (response.isSuccessful) {
                    mOnReceiveDataListener?.onReceiveData(response.body()?.data!!)
                    add(response.body()?.data!!)
                } else {
                    mOnReceiveDataListener?.onFailureToReceiveData()
                }
            }

        })
    }

}
