package com.example.recyclerviewactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    /*Above, lateint means,"I am declaring this variable now but I will give it a value later"
    * If we don't put this, Kotlin will force us to initialize the variable and we can't do
    * that until the Layout has been set.{setContentView(R.layout.activity_main) in onCreate below}
    * In Kotlin, a class can be used to define a variable type
    * RecyclerView is a class provided in Android. It gives you a blueprint for creating
    * recycler views. So for the line above, we are saying,"create a variable of the type
    * RecyclerView" It therefore can access the properties of the class
    * ContactAdapter is the class we have built here. So we are saying that the variable
    * adapter is of the type ContactAdapter*/

    private val contacts = listOf(
        Contact("Gloria", "0172829192"),
        Contact("Angel",   "0725161801"),
        Contact("Kate",  "0671812029"),
        Contact("Cassandra",  "0791812029"),
        Contact("Aurora",  "0718120629"),
        Contact("Rhianon",  "0769812029"),
        Contact("Genevieve", "0172829192"),
        Contact("Angel",   "0725161801"),
        Contact("Kate",  "0671812029"),
        Contact("Cassandra",  "0791812029"),
        Contact("Aurora",  "0718120629"),
        Contact("Rhianon",  "0769812029"),
        Contact("Talia", "0172829192"),
        Contact("Annita",   "0725161801"),
        Contact("Kazimirah",  "0671812029"),
        Contact("Cassy",  "0791812029"),
        Contact("Tina",  "0718120629"),
        Contact("Rissa",  "0769812029")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*This loads the xml layout of the activity_main*/

        recyclerView = findViewById(R.id.recyclerView)//Initializing the variable recyclerView
        /*This grabs the recyclerView inside activity_main so that we can use it in Kotlin*/

        recyclerView.layoutManager = LinearLayoutManager(this)
        /*LayoutManager tells RecyclerView how to arrange every list item. I was a bit confused
        * on why we were repeating this here when in item_contact.xml, we had already used
        * LinearLayout but then I noticed that that one was for how we organize the text
        * inside a single list item. This is how we arrange the list items*/

        adapter = ContactAdapter(contacts)//Initializing the variable adapter

        recyclerView.adapter = adapter
        /*Aside from giving the recyclerView a layoutManager, you also have to give it an
        * adapter which basically gives it the data to display
        * I know it's a bit confusing that the name of the adapter is also adapter but I'm
        * leaving it like that so that later own I can know that it's okay to give the adapter
        * I've created the name adapter*/
    }
}

data class Contact(val name: String, val phone: String)

class ContactAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>()
/*In Kotlin, the colon : means the class inherits from or implements another class/interface.
  Here: RecyclerView.Adapter<ContactAdapter.ViewHolder>(),
  RecyclerView.Adapter is a generic class provided by Android.

  What does “generic” means? A generic is like a placeholder for a type. Instead of saying:
  class BoxString { val value: String } OR
  class BoxInt { val value: Int }
  You can write one class that works for any type: class Box<T>(val value: T). T is a type
  parameter — a placeholder for any type.Later, you can create boxes of different types:
  val box1 = Box<String>("Hello") OR val box2 = Box<Int>(123)

  Here, the generic type <ContactAdapter.ViewHolder> tells it:
  “Each item in the RecyclerView will be represented by a ViewHolder class defined
  inside ContactAdapter.”
  What is a ViewHolder? A ViewHolder is a wrapper for each row in the RecyclerView. It holds
  references to the views (TextViews, ImageViews) for one row.
  So the generic type <ContactAdapter.ViewHolder> tells RecyclerView.Adapter:
  “This adapter uses ViewHolder objects to manage its rows.”
  So In summary:
  You are creating a class ContactAdapter.
  It inherits from RecyclerView.Adapter.
  It tells RecyclerView that it will use ContactAdapter.ViewHolder objects for each row.
  It stores a list of contacts that the adapter will display.*/
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*class ViewHolder(view: View): You are declaring a new class named ViewHolder. It has a
        constructor parameter called view of type View. This view represents the layout of a
        single row/item in the RecyclerView. The colon is inheritance
        RecyclerView.ViewHolder(view): RecyclerView.ViewHolder is a built-in class in Android.It
        wraps a single item view and provides the RecyclerView machinery for recycling views.By
        writing (view) here, you are passing the row view to the parent class constructor.
        So what the statement above means is,"I am creating a class called ViewHolder that extends
        RecyclerView.ViewHolder, and when I create it, I pass the row’s view to the parent
        RecyclerView.ViewHolder class."*/
        val name: TextView = view.findViewById(R.id.person_name)
        /*The reason you have to write view here before findViewById unlike in MainActivity where we just used
        * findViewById directly is because ContactAdapter is not an activity. Activities have direct access to
        * this method, however for classes, you have to use the parameter(view) in this case to get access to
        * findViewById*/
        val phone: TextView = view.findViewById(R.id.phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*Above, ViewHolder stores references to views inside a row, so you don’t call findViewById repeatedly.
        * onCreateViewHolder creates an instance of that row (inflates the XML) and wraps it in a ViewHolder.
        * Later, onBindViewHolder fills in the actual data for that specific row.
        * Code explanation:
        * onCreateViewHolder is a method we override from the parent class(Recycler.Adapter) above
        * Overriding is when a subclass provides its own implementation of a function that is already defined
        * in its parent class or interface.The whole line above comes automatically when you click the function
        * In the brackets above, parent: ViewGroup means the container where the row we are about to create
        * will live. In this case the viewGroup is the RecyclerView*/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
        /*Code Explanation:
         LayoutInflater.from(parent.context): LayoutInflater is a class that turns XML layouts into actual
         View objects. from(context) gets a LayoutInflater instance from a given context.
         parent.context → the context of the RecyclerView (or parent ViewGroup).
         Context is needed because the inflater needs to know theme, dimensions, and resources.
         .inflate(R.layout.item_contact, parent, false): Now we call inflate() on the inflater:
         a) R.layout.item_contact
         This is the XML layout file for a single row.
         Think of it as a blueprint for one contact row (with TextViews for name and phone).
         b) parent
         parent is the ViewGroup that will eventually contain this view.
         RecyclerView passes itself (the container) as parent.
         Why do we pass it? So the inflater can know how to set layout parameters like width, height, margins.
         c) false
         This means: don’t attach the new view to the parent yet.
         Why? RecyclerView will attach it automatically at the right time.
         If we pass true, it would immediately add the view to the parent, which can break RecyclerView behavior.*/
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        /*holder: ViewHolder -> The ViewHolder instance for the current row. We've already created it above
        * pos: Int -> The position or index of the current item in your list (contacts)
        * So this part basically says: "Bind the data for the contact at position pos into the views in this
        * ViewHolder.*/
        holder.name.text = contacts[pos].name
        holder.phone.text = contacts[pos].phone
        /*Here we are just filling the row created above with data depending on the position
        * So for example, contacts[1]name and contacts[1]phone
        * Why is this separate from the onCreateViewHolder? So that when a row scrolls off-screen, RecyclerView
        * can pass its ViewHolder back into onBindViewHolder with a new pos.It can then update the data to match
        * the new item at that position.*/
    }

    override fun getItemCount() = contacts.size /*This is just getting the size of the list so that recyclerView
    knows how many items it is displaying*/

    /*So in summary, the steps are:
    * Create ViewHolder class, which stores references to the TextViews for name and phone.
    * onCreateViewHolder inflates the row layout from XML.
    * onBindViewHolder fills the views with actual contact data.
    * getItemCount simply tells the RecyclerView how many contacts there are.”*/
}
