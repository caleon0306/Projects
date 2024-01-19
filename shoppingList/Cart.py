class Cart:
    #initialize cart
    def __init__(self):
        self.meals = []

    #returns True is meals is empty, False otherwise
    def isEmpty(self):
        if self.meals == []:
            return True
        return False

    #add a Meal to Cart
    def addMeal(self, newMeal):
        self.meals.append(newMeal)

    #remove a Meal from Cart
    #returns False if empty or you choose not to remove. returns True if item re
    def removeMeal(self):
        if self.isEmpty():
            print()
            print("Cart is empty")
            return False
        count = 0
        print()
        print("Remove Meal Options:")
        for m in self.meals:
            print(str(count) + ' ' + m.getName())
            count += 1
        selection = int(input("Select a Meal to Remove(-1 to end selection):"))
        if selection == -1:
            return False
        if 0 <= selection < len(self.meals):
            self.meals.remove(self.meals[selection])
            print()
            print("Meal Removed")
            return True
        else:
            print()
            print("Invalid selection")
            return True
    
    #see if a meal is already in self.meals list
    #returns True is meal with matching name is found, otherwise False
    def searchForMeal(self, lookMeal):
        #lm is the name of meal looking for
        lm = lookMeal.getName()
        for m in self.meals:
            if lm == m.getName():
                return True
        return False

    #return self.meals
    def getMeals(self):
        return self.meals

    #create a shopping list in order
    def generateShopList(self):
        if self.isEmpty():
            return []
        else:
            return self.combineIngredients(self.meals)

    #combine 2 lists of ingredients into one sorted list
    def combineIngredients(self, meals):
        #rightH is always populated as long as a non-empty list is passed through
        leftH = meals[:len(meals)//2]
        rightH = meals[len(meals)//2:]
        if leftH == []:
            return rightH[0].getIngredients()
        elif len(rightH) == 1:
            rightI = rightH[0].getIngredients()
            combineI = []
            rPos = 0
            addedR = []
            for i in leftH[0].getIngredients():
                same = False
                #add rightH at rPos while rPos item section is lower than i
                while rPos < len(rightI) and i[0].compareSection(rightI[rPos][0]) > 0:
                    if rPos not in addedR:
                        combineI.append(rightI[rPos])
                    rPos += 1
                esPos = rPos
                #compare when rPos and i sections are equal for matching ingredient and mark that ingredient as added into addedR and mark for i
                while same == False and esPos < len(rightI) and i[0].compareSection(rightI[esPos][0]) == 0:
                    if i[0].compareTo(rightI[esPos][0]) == True:
                        combineI.append([i[0], i[1] + rightI[esPos][1]])
                        same = True
                        addedR.append(esPos)
                    esPos += 1
                #if no duplicate is already added add i
                if same == False:
                    combineI.append(i)
            #add any remaining rPos
            while rPos < len(rightI):
                if rPos not in addedR:
                        combineI.append(rightI[rPos])
                rPos += 1
            return combineI
        else:
            leftH = self.combineIngredients(leftH)
            rightH = self.combineIngredients(rightH)
################################################################
            combineI = []
            rPos = 0
            addedR = []
            for i in leftH:
                same = False
                #add rightH at rPos while rPos item section is lower than i
                while rPos < len(rightH) and i[0].compareSection(rightH[rPos][0]) > 0:
                    if rPos not in addedR:
                        combineI.append(rightH[rPos])
                    rPos += 1
                esPos = rPos
                #compare when rPos and i sections are equal for matching ingredient and mark that ingredient as added into addedR and mark for i
                while same == False and esPos < len(rightH) and i[0].compareSection(rightH[esPos][0]) == 0:
                    if i[0].compareTo(rightH[esPos][0]) == True:
                        combineI.append([i[0], i[1] + rightH[esPos][1]])
                        same = True
                        addedR.append(esPos)
                    esPos += 1
                #if no duplicate is already added add i
                if same == False:
                    combineI.append(i)
            #add any remaining rPos
            while rPos < len(rightH):
                if rPos not in addedR:
                        combineI.append(rightH[rPos])
                rPos += 1
            return combineI

    #print the Cart ingredients        
    def printShopList(self):
        printList = self.generateShopList()
        print()
        if printList == []:
            print("The Cart is empty")
        else:
            print("Shopping List:")
            for i in printList:
                #used for testing purposes
                #print("Section: " + ' ' + str(i[0].getSection()) + ' ' + str(i[1]) + ' ' + i[0].getMeasurment() + ' ' + i[0].getName())
                print(str(i[1]) + ' ' + i[0].getMeasurment() + ' ' + i[0].getName())
