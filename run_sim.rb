#!/usr/bin/ruby
require 'fileutils'
require 'mkmf'

has_code = File.exists?(ARGV[0])
has_sim_code = File.exists?(ARGV[1])
abort "Error: Robot code doesn't exist in project #{ARGV[0]}" unless has_code
abort "Error: Sim Robot code doesn't exist in project #{ARGV[0]}" unless has_sim_code
abort "Error: Ant not found. Try installing with 'brew install ant'." unless find_executable 'ant'

cur_dir = Dir.pwd

Dir.chdir(ARGV[0])
system('ant jar')

Dir.chdir(cur_dir)

jar_file = ARGV[0] + "/dist/FRCUserProgram.jar"

tmp_dir = Dir.pwd + "/tmp"
`rm -rf #{tmp_dir}`

Dir.mkdir(tmp_dir) unless File.exists?(tmp_dir)
FileUtils.cp jar_file, tmp_dir + "/FRCUserProgram.jar"

dist_dir = Dir.pwd + "/dist"
FileUtils.cp dist_dir + "/FakeWPILib.jar", tmp_dir + "/FakeWPILib.jar"

Dir.chdir('tmp')
Dir.mkdir('classes')
Dir.chdir('classes')

`jar -xf ../FRCUserProgram.jar`
FileUtils.cp "META-INF/MANIFEST.MF", "META-INF/MANIFEST.MF.old"

#{}`jar -xf ../FakeWPILib.jar`
`cp -r ../../bin/ .`
FileUtils.cp "META-INF/MANIFEST.MF.old", "META-INF/MANIFEST.MF"
FileUtils.rm "META-INF/MANIFEST.MF.old"

# Copy sim robot class files
`cp -r #{cur_dir}/#{ARGV[1]}/bin/ .`

Dir.chdir('..')
`jar -cmvf classes/META-INF/MANIFEST.MF to_sim.jar -C classes .`

system("java -jar to_sim.jar")

