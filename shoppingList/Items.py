class Item:
    #create the variables for the name, section, and measurment
    def __init__(self, n, s, m):
        self.name = n
        self.section = None
        #measurment is how the Item is weighed
        self.measurment = m
        self.setSection(s)
    
    #set the name
    def setName(self, name):
        self.name = name
    
    #get the name
    def getName(self):
        return self.name
    
    #set the section
    #lowest section is traveled to first
    def setSection(self, section):
        secStartPos = 33
        if section == 32:
            self.section = 2
        elif section == 31:
            self.section = 1
        elif section == 33:
            self.section = 10
        elif section == 34:
            self.section = 30
        else:
            self.section = secStartPos - section
        

    #get the section
    def getSection(self):
        return self.section
    
    #set the measurment
    def setMeasurment(self, measurment):
        self.measurment = measurment

    #get the measurment
    def getMeasurment(self):
        return self.measurment

    #compare two Item section and return -1 if self is lower, 1 if self is higher, and 0 if equal
    def compareSection(self, other):
        if self.section < other.section:
            return -1
        if self.section > other.section:
            return 1
        else:
            return 0

    #compare 2 items
    def compareTo(self, other):
        return self.name == other.getName()

    #return section and name separated by a space
    def toString(self):
        return str(self.section) + ' ' + self.name + ' ' + self.measurment