package jobs

organizationFolder('GitHub Enterprise') {
	description('Merge requests from GitHub')

	triggers {
		periodic(1440)
	}

	orphanedItemStrategy {
		discardOldItems {
			numTokeep(10)
		}
	}

	organizations {
		github {
			repoOwner("linuxshokunin")
			apiUri("https://github.com/linuxshokunin")
			credentialsId("shin.imai")
		}
	}

	configure {
		def traits = it / navigators / 'org.jenkinsci.plugins.github__branch__source.GitHubSCMNavigator' / traits
		traits << 'org.jenkinsci.plugins.github__branch__source.BranchDiscoveryTrait' {
			strategyId(1)
		}
		traits << 'org.jenkinsci.plugins.github__branch__source.BranchDiscoveryTrait' {
			strategyId(1)
		}
		traits << 'jenkins.scm.impl.trait.WildCardSOHeadFilterTrait' {
			includes('master PR*')
			excludes('')
		}
		traits << 'jenkins.plugins.git.traits.PruneStaleBranchTrait' {
		}
	}

	projectFactories {
		workflowMultiBranchProjectFactory {
			scriptPath("Jenkinsfile.groovy")
		}
	}

}
