package fr.android.androidexercises

import android.os.Parcel
import android.os.Parcelable

data class Book(val isbn: String, val title: String, val price: String, val cover: String, val synopsis: Array<String>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createStringArray())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(isbn)
        dest?.writeString(title)
        dest?.writeString(price)
        dest?.writeString(cover)
        dest?.writeStringArray(synopsis)
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}