import math

class Meal:
    #define the name of the meal and create an empty ingredient list
    def __init__(self, n):
        self.name = n
        self.ingredients = []

    #get Meal name
    def getName(self):
        return self.name

    #add ingredient to ordered list of Item
    def addIngredient(self, i):
        ingredient = i[0]
        if self.ingredients == []:
            self.ingredients.append(i)
        else:
            position = self.findPos(0, len(self.ingredients) - 1, ingredient.getSection())
            if position == -1:
                self.ingredients.append(i)
            else:
                self.ingredients.insert(position, i)

    #search a sorted list of ingredients to find the position to add new ingredient            
    def findPos(self, min, max, thisSec):
        middle = math.floor((max + min)/2)
        midItem = self.ingredients[middle]
        midSec = midItem[0].getSection()
        if thisSec == midSec:
            return middle
        elif thisSec < midSec:
            if min == middle:
                return middle
            return self.findPos(min, middle - 1,thisSec)
        elif thisSec > midSec:
            if min == len(self.ingredients) - 1:
                return -1
            elif max == middle:
                return middle + 1
            return self.findPos(middle + 1, max, thisSec)
        
    #get a list of ingredients
    def getIngredients(self):
        return self.ingredients
    
    #get item sections
    def printSections(self):
        for i in self.ingredients:
            print(i[0].toString())

    #print the list of ingredients in the Meal
    def printIngredients(self):
        for i in self.ingredients:
            if i[2] == "":
                print(str(i[1]) + " " + str(i[0].getName()))
            else:
                print(str(i[1]) + " " + i[2] + " " + str(i[0].getName()))