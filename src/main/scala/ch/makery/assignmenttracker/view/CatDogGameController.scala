package ch.makery.assignmenttracker.view

import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.control.Label
import scalafx.event.ActionEvent
import ch.makery.assignmenttracker.model.Dog
import ch.makery.assignmenttracker.model.Cat
import ch.makery.assignmenttracker.model.Food
import ch.makery.assignmenttracker.model.DogFood
import ch.makery.assignmenttracker.model.CatFood

@sfxml
class CatDogGameController(
    private val dogImage : ImageView,
    private val catImage : ImageView,
    private val dialogLabel : Label,
    private val dogFullnessLabel : Label,
    private val catFullnessLabel : Label
){

    dogImage.setImage(new Image("https://cdn-icons-png.flaticon.com/512/620/620851.png"))
    catImage.setImage(new Image("https://cdn-icons-png.flaticon.com/512/220/220124.png"))

    val cat = new Cat("Snow")
    val dog = new Dog("Dodo")

    val foodArray = Array[Food](new CatFood, new DogFood)

    dogFullnessLabel.text = dog.stomachFullness.toString
    catFullnessLabel.text = cat.stomachFullness.toString

    def updateStomachFullness() : Unit = {
        dogFullnessLabel.text = dog.stomachFullness.toString
        catFullnessLabel.text = cat.stomachFullness.toString
    }
    def handleDogMakeSound(action : ActionEvent) {
        dialogLabel.text = dog.makeSound()
        updateStomachFullness()
    }
    def handleDogIntroduction(action : ActionEvent) {
        dialogLabel.text = dog.introduction()
        updateStomachFullness()
    }
    def handleDogEat(action : ActionEvent) {
        if(! dog.isFull) {
            dialogLabel.text = dog.eat(foodArray(0))
        } else {
            dialogLabel.text = dog.vomit()
        }
        updateStomachFullness()
    }
    def handleDogHitCat(action : ActionEvent) {
        dialogLabel.text = dog.hit(cat)
        updateStomachFullness()
    }

    def handleCatMakeSound(action : ActionEvent) {
        dialogLabel.text = cat.makeSound()
        updateStomachFullness()
    }
    def handleCatIntroduction(action : ActionEvent) {
        dialogLabel.text = cat.introduction()
        updateStomachFullness()
    }
    def handleCatEat(action : ActionEvent) {
        if(! cat.isFull) {
            dialogLabel.text = cat.eat(foodArray(1))
        } else {
            dialogLabel.text = cat.vomit()
        }
        updateStomachFullness()
    }
    def handleCatHitDog(action : ActionEvent) {
        dialogLabel.text = cat.hit(dog)
        updateStomachFullness()
    }
}
