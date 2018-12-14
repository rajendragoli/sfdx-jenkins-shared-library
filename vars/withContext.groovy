#!/usr/bin/env groovy
import com.claimvantage.jsl.Org

def glob = 'config/project-scratch-def.*.json'  // Default
def variable = 'org'                            // Default

def call(Closure body = null) {
    
    echo "Before body"
    if(body) body()
    echo "After body"
    
    /*
    for (def file : findFiles(glob: glob)) {
        echo "Found ${file.path}"
        def org = new Org(projectScratchDefPath: file.path)
        if (body) {
            body([variable: org])
        }
    }
    
    return this
    */
}
