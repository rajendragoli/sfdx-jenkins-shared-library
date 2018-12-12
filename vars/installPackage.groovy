import com.claimvantage.jsl.Org

// Name is just for info purposes
def call(Org org, name, packageVersionId, packagePassword) {

    echo "Install ${name} package in ${org}"

    shWithStatus "sfdx force:package:install --targetusername ${org.username} --package ${packageVersionId} --installationkey ${packagePassword} --wait 15 --noprompt"
    
    return this
}