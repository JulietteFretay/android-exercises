package fr.android.androidexercises

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import fr.android.androidexercises.fragments.FragmentDetails
import fr.android.androidexercises.fragments.FragmentList

class LibraryActivity : AppCompatActivity(), FragmentList.Listener, FragmentDetails.ListenerDetails {

    private var currentDetailsFragment: Fragment? = null

    override fun onBookSelected(book: Book) {
        val detailsFragment = FragmentDetails.newInstance(book, resources.configuration.orientation == 2)
        currentDetailsFragment = detailsFragment
        if(resources.configuration.orientation == 1) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.list_fragment, detailsFragment, FragmentDetails::class.java.name).addToBackStack("list_fragment")
                    .commit()
        }else{
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, detailsFragment, FragmentDetails::class.java.name).addToBackStack("details_fragment")
                    .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment, FragmentList(), FragmentList::class.java.name)
                .commit()
        if(resources.configuration.orientation == 2){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, FragmentDetails(), FragmentDetails::class.java.name).addToBackStack("details_fragment")
                    .commit()
        }else{
            //Not working
            supportFragmentManager.beginTransaction()
                    .remove(FragmentDetails()).addToBackStack(null)
                    .commit()
        }
    }

    override fun onCloseFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
                .replace(R.id.list_fragment, FragmentList(), FragmentList::class.java.name).addToBackStack("list_fragment")
                .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if(currentDetailsFragment != null) {
            supportFragmentManager.putFragment(outState!!, "savedDetailFragment", currentDetailsFragment!!)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val frag = supportFragmentManager.getFragment(savedInstanceState!!, "savedDetailFragment")
        if(frag != null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, frag, FragmentDetails::class.java.name).addToBackStack("details_fragment")
                    .commit()
        }
    }

}


