package ch.makery.assignmenttracker.model
import ch.makery.assignmenttracker.model.Animal

class Dog (_name: String) extends Animal(_name) {
    // overide some of the methods from Animal
    def makeSound() : String = {
        decreaseStomachFullness(10)
        "woof woof"
    }
    def makeSound(soundToMake : String) : String = {
        decreaseStomachFullness(10)
        "woof woof, " + soundToMake
    }
    def introduction(): String = {
        var hungryStatus : String = "hungry"
        if(isFull) {
            hungryStatus = "not hungry"
        }
        makeSound("Hi, I am a dog, My name is " + _name + ", I am " + hungryStatus)
    }
}
