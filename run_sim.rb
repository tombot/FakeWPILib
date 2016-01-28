#!/usr/bin/ruby
require 'fileutils'
require 'mkmf'

has_code = File.exists?(ARGV[0])
has_sim_code = File.exists?(ARGV[1])
abort "Error: Robot code doesn't exist in project #{ARGV[0]}" unless has_code
abort "Error: Sim Robot code doesn't exist in project #{ARGV[0]}" unless has_sim_code
abort "Error: Ant not found. Try installing with 'brew install ant'." unless find_executable 'ant'

cur_dir = Dir.pwd

FileUtils.rm_rf(cur_dir + "/tmp")
has_sim = ARGV[1] != ".."

# compile robot code
Dir.chdir(ARGV[0])
system('ant jar')

# compile fake wpi lib
Dir.chdir(cur_dir)
system('ant jar')

# move fake wpi lib jar to sim robot dir
`mkdir #{ARGV[1] + "/lib/"}`
FileUtils.cp(cur_dir + "/dist/FakeWPILib.jar", ARGV[1] + "/lib/") if has_sim
FileUtils.cp(ARGV[0]+ "/dist/FRCUserProgram.jar", ARGV[1] + "/lib/") if has_sim

# compile sim robot
Dir.chdir(ARGV[1])
system('ant compile') if has_sim

Dir.chdir(cur_dir)

# remove tmp dirs
tmp_dir = Dir.pwd + "/tmp"
`rm -rf #{tmp_dir}`

# copy robot jar
jar_file = ARGV[0] + "/dist/FRCUserProgram.jar"
Dir.mkdir(tmp_dir) unless File.exists?(tmp_dir)
FileUtils.cp jar_file, tmp_dir + "/FRCUserProgram.jar"

# make temp directories
Dir.chdir('tmp')
Dir.mkdir('classes')
Dir.chdir('classes')

# uncompress robot jar
`jar -xf ../FRCUserProgram.jar`
FileUtils.cp "META-INF/MANIFEST.MF", "META-INF/MANIFEST.MF.old"

# copy compiled fake wpi lib
`cp -r "../../bin/" "."`
FileUtils.cp "META-INF/MANIFEST.MF.old", "META-INF/MANIFEST.MF"
FileUtils.rm "META-INF/MANIFEST.MF.old"

# Copy sim robot class files
`cp -r "#{cur_dir}/#{ARGV[1]}/bin/" "."` if has_sim

# make test harness
Dir.chdir('..')
`jar -cmvf classes/META-INF/MANIFEST.MF to_sim.jar -C classes .`

sim_robot_name = "com.team254.frc2016.sim.SimRobot"

# run test harness
system("java -jar to_sim.jar #{sim_robot_name}")

