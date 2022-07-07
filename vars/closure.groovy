def call(body) {
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()

	def param = config.param

}

/*
closure {
	param = "First"
}
*/
