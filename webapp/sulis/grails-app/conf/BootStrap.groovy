class BootStrap {
	
	def fixtureLoader

    def init = { servletContext ->
		
		environments {
			development {
				println "DEV MODE BOOTSTRAP"
				fixtureLoader.load("initData")
			}
			
			test {
				println "TEST MODE BOOTSTRAP"
				fixtureLoader.load("initData")
			}
		}
    }
    def destroy = {
    }
	
	
		
}

