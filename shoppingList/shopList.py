import Items, Meals, MealsList, ItemsList, Cart

ITEMFILE = "C:/Users/caleo/Career/projects/shoppingList/itemFile.txt"
MEALFILE = "C:/Users/caleo/Career/projects/shoppingList/mealFile.txt"

def main():
    #meals = MealsList.createdMeals
    userMealCart = Cart.Cart()
    menuSelection = 0
    items = initializeItemList()
    meals = intializeMealsList(items)
    #let user add and remove meals from userMealCart
    while menuSelection != -1:
        menuSelection = menu()
        #add meals
        if menuSelection == 1:
            mealSelect(meals, userMealCart)
        #remove meals
        elif menuSelection == 2:
            removeMeals(userMealCart)
        #print current Cart ingredients in a list    
        elif menuSelection == 3:
            userMealCart.printShopList()
        elif menuSelection == 4:
            if(createItem(items)):
                print("Item Created!")
            else:
                print("Item not created.")
        #check for invalid menu selection or exit condition
        else:
            if menuSelection != -1:
                print()
                print("Invalid menu selection")
    userMealCart.printShopList()  
    #printShopList(userMealCart.generateShopList())

# get all lines from ITEMFILE and store them as Item in items
def initializeItemList():
    try:
        reader = open(ITEMFILE, 'r')

        items = []
        for line in reader:
            info = line.replace('\n','')
            info = info.split(',')
            items.append(Items.Item(info[0], int(info[1]), info[2]))
        reader.close()
        return items 
    except:
        print("Error opening " + ITEMFILE)

#get all meals from MEALFILE and store them as Meal in meals
def intializeMealsList(items):
    try:
        reader = open(MEALFILE, "r")
        meals = []
        lookingForMeal = True
        currentMeal = None
        for line in reader:
            #check if current meal ingredients ended
            if line == '\n':
                meals.append(currentMeal)
                lookingForMeal = True
            else:
                info = line.replace('\n','')
                #check if waiting on meal title
                if lookingForMeal:
                    currentMeal = Meals.Meal(info)
                    lookingForMeal = False
                #not waiting on title means anything is an ingredient
                else:
                    info = info.split(',')
                    for i in items:
                        if info[0] == i.getName():
                            currentMeal.addIngredient([i, int(info[1])])
        meals.append(currentMeal)
        return meals
    except:
        print("Error opening " + MEALFILE)

#get user input for action
def menu():
    selection = 0
    print()
    print("Please select an option:")
    print("1 to add a meal")
    print("2 to remove a meal")
    print("3 to print current shopping list")
    print("4 to create a new item")
    selection = int(input("Selection(-1 to finish):"))
    return selection

#get meals to be added to userMealCart
def mealSelect(meals, umc):
    selecting = True
    #loop through adding meals until user exits
    while selecting:
        print()
        print("Add Meal Options:")
        #num prints corresponding in position of list
        num = 0
        #print meal options
        for m in meals:
            print(str(num) + ' ' + m.getName())
            num += 1
        selection = int(input("Select a Meal to Add(-1 to end selection):"))
        #check for valid meal add input
        if 0 <= selection < len(meals):
            if umc.searchForMeal(meals[selection]):
                print()
                print("Meal Already Added")
            else:
                umc.addMeal(meals[selection])
        #check if user is done with selecting
        else:
            if selection == -1:
                selecting = False
            else:
                print()
                print("Invalid Input")

#let the user loop through removing Meals from Cart until they select to stop
def removeMeals(umc):
    removing = True
    while removing:
        removing = umc.removeMeal()
        if umc.isEmpty():
            removing = False

#allow the user to create a new Item
def createItem(items):
        name = input("Item Name:")
        for i in items:
            if name == i.getName():
                print("Item name already exists")
                return False
        sec = int(input("Item Section:"))
        weight = input("Item weighed in:")
        print()
        print("Confirm Item:")
        print("Name: " + name)
        print("Section: " + str(sec))
        print("Weight: " + weight)
        confirm = input("Confirm(y/n):")
        confirm = confirm.capitalize()
        if confirm == "Y":
            try:
                writer = open(ITEMFILE, 'a')
                writer.write("\n")

                writer.write(name + ',' + str(sec) + ',' + weight)
                writer.close()
                items.append(Items.Item(name,sec,weight))
                return True
            except:
                print("Error opening " + ITEMFILE)
        else:
            return False

if __name__ == "__main__":
    main()