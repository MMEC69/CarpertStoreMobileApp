package cinec.edu.carpertstoremmec.data

sealed class Category(val category: String) {

    object Wool: Category("Wool")
    object Nylon: Category("Nylon")
    object Polyester: Category("Polyester")
    object Polypropylene: Category("Polypropylene")
    object Triexta: Category("Triexta")


}