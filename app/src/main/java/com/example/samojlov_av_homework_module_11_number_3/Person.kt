package com.example.samojlov_av_homework_module_11_number_3

import android.os.Parcel
import android.os.Parcelable

data class Person(
    val name: String?,
    val surname: String?,
    val phone: String?,
    val address: String?
) : Parcelable {
    override fun toString(): String {
        return "Имя: $name\nФамилия: $surname\n\nТелефон: $phone\n\nАдрес: $address"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(surname)
        dest.writeString(phone)
        dest.writeString(address)
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}