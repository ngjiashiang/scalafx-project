package ch.makery.assignmenttracker.model

abstract class Animal(val name : String) {
    var stomachFullness : Int = 100
    def makeSound() : String
    def makeSound(soundToMake : String) : String
    def introduction(): String
    def hit(animalToHit : Animal) : String = {
        decreaseStomachFullness(10)
        animalToHit.decreaseStomachFullness(10)
        animalToHit.makeSound("Ouch!")
    }
    def isFull() : Boolean = {
        if(stomachFullness == 100) {
            return true
        }
        false
    }
    def vomit() : String = {
        decreaseStomachFullness(10)
        "Too full. Vomitting"
    }
    def increaseStomachFullness(percentage : Int) : Unit = {
        stomachFullness += percentage
    }
    def decreaseStomachFullness(percentage : Int) : Unit = {
        if(stomachFullness >= percentage) {
            stomachFullness -= percentage
        }
    }
    def eat(food : Food) : String = {
        increaseStomachFullness(10)
        "Yum Yum"
    }
}
